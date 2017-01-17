/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unioeste.cascavel.avaliacaodocentes.persistence;

import br.unioeste.cascavel.avaliacaodocentes.controller.Exception.ItemNotFoundException;
import com.orientechnologies.orient.core.command.OCommandRequest;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author romulo
 * @param <T>
 */
public abstract class GraphPersistence<T extends Persistable> implements Persistence<T> {

    private final OrientGraph graph;
    private final Class<T> classe;
    private final Map<Persistable, Vertex> visitedItemsInRecursion;
    private final Map<Vertex, Fill> retrievedItemsInRecursion;
    private final Set<Vertex> deletedItemsInRecursion;

    public GraphPersistence(Class<T> classe) {
        this.graph = new OrientGraph("remote:localhost/AvaliacaoDocentes", "root", "root");
        this.classe = classe;
        this.visitedItemsInRecursion = new HashMap<>();
        this.retrievedItemsInRecursion = new HashMap<>();
        this.deletedItemsInRecursion = new HashSet<>();
    }

    protected abstract T buildEntity(Fill primaryKey);

    @Override
    public Collection<T> list() {
        synchronized (GraphPersistence.class) {
            Collection<T> lista = new ArrayList<>();
            Iterable<Vertex> vertices = graph.getVerticesOfClass(classe.getSimpleName());
            for (Vertex vertex : vertices) {
                Fill fill = getFill(vertex);
                T entity = buildEntity(fill);
                entity.fillEntity(fill);
                lista.add(entity);
            }
            graph.commit();
            return lista;
        }
    }

    @Override
    public void create(T t) {
        synchronized (GraphPersistence.class) {
            this.visitedItemsInRecursion.clear();
            createVertex(t);
            graph.commit();
        }
    }

    private Vertex createVertex(Persistable persistable) {
        Fill fill = persistable.getValues();
        OrientVertex vertex = graph.addVertex("class:" + persistable.getClass().getSimpleName());
        this.visitedItemsInRecursion.put(persistable, vertex);
        for (Map.Entry<String, Object> entry : fill) {
            if (entry.getValue() == null) {
            } else if (entry.getValue() instanceof Number) {
                vertex.setProperty(entry.getKey(), entry.getValue());
            } else if (entry.getValue() instanceof Boolean) {
                vertex.setProperty(entry.getKey(), entry.getValue());
            } else if (entry.getValue() instanceof String) {
                vertex.setProperty(entry.getKey(), entry.getValue());
            } else if (entry.getValue().getClass().isPrimitive()) {
                vertex.setProperty(entry.getKey(), entry.getValue());
            } else if (entry.getValue() instanceof Persistable) {
                Persistable child = (Persistable) entry.getValue();
                Vertex createVertex;
                if (!this.visitedItemsInRecursion.containsKey(child)) {
                    createVertex = createVertex(child);
                } else {
                    createVertex = this.visitedItemsInRecursion.get(child);
                }
                Edge edge = vertex.addEdge("edge_" + entry.getKey(), createVertex);
                edge.setProperty("vinculo", "simples");
            } else if (entry.getValue() instanceof Collection) {
                try {
                    Collection<Persistable> cast = (Collection<Persistable>) entry.getValue();
                    for (Persistable child : cast) {
                        Vertex createVertex;
                        if (!this.visitedItemsInRecursion.containsKey(child)) {
                            createVertex = createVertex(child);
                        } else {
                            createVertex = this.visitedItemsInRecursion.get(child);
                        }
                        Edge edge = vertex.addEdge("edge_" + entry.getKey(), createVertex);
                        edge.setProperty("vinculo", "colecao");
                    }
                } catch (ClassCastException ex) {
                    System.err.println("Classes devem implementar Persistable");
                }
            } else if (entry.getValue() instanceof Map) {
                Map<Persistable, Persistable> cast = (Map<Persistable, Persistable>) entry.getValue();
                for (Map.Entry<Persistable, Persistable> child : cast.entrySet()) {
                    Vertex keyVertex;
                    if (!this.visitedItemsInRecursion.containsKey(child.getKey())) {
                        keyVertex = createVertex(child.getKey());
                    } else {
                        keyVertex = this.visitedItemsInRecursion.get(child.getKey());
                    }
                    Vertex valueVertex;
                    if (!this.visitedItemsInRecursion.containsKey(child.getValue())) {
                        valueVertex = createVertex(child.getValue());
                    } else {
                        valueVertex = this.visitedItemsInRecursion.get(child.getValue());
                    }
                    Edge edge = vertex.addEdge("edge_" + entry.getKey(), keyVertex);
                    edge.setProperty("link_" + entry.getKey(), valueVertex);
                    edge.setProperty("vinculo", "map");
                }
            }
        }
        return vertex;
    }

    private Collection<StringBuilder> getConditions(Persistable persistable) {
        Collection<StringBuilder> conditions = new ArrayList<>();
        StringBuilder condition;
        Fill fill = persistable.getPrimaryKey();
        for (Map.Entry<String, Object> entry : fill) {
            condition = new StringBuilder();
            if (entry.getValue().getClass().isPrimitive()) {
                condition.append(entry.getKey());
                condition.append(" = ");
                condition.append(entry.getValue().toString());
            } else if (entry.getValue() instanceof Number) {
                condition.append(entry.getKey());
                condition.append(" = ");
                condition.append(entry.getValue().toString());
            } else if (entry.getValue() instanceof Boolean) {
                condition.append(entry.getKey());
                condition.append(" = ");
                condition.append(entry.getValue().toString());
            } else if (entry.getValue() instanceof String) {
                condition.append(entry.getKey());
                condition.append(" = \"");
                condition.append(entry.getValue());
                condition.append("\"");
            } else if (entry.getValue() instanceof Persistable) {
                Persistable child = (Persistable) entry.getValue();
                Collection<StringBuilder> childConditions = getConditions(child);
                for (StringBuilder childCondition : childConditions) {
                    condition.append("OUT(\"");
                    condition.append("edge_");
                    condition.append(entry.getKey());
                    condition.append("\")");
                    condition.append(".");
                    condition.append(childCondition);
                    conditions.add(condition);
                    condition = new StringBuilder();
                }
            }
            if (condition.length() > 0) {
                conditions.add(condition);
            }
        }
        return conditions;
    }

    private Iterable<Vertex> retrieveVertex(Persistable persistable) throws ItemNotFoundException {
        StringBuilder strQuery = new StringBuilder();
        strQuery.append("SELECT FROM ");
        strQuery.append(persistable.getClass().getSimpleName());
        strQuery.append(" WHERE ");
        Collection<StringBuilder> conditions = getConditions(persistable);
        Iterator<StringBuilder> iterator = conditions.iterator();
        while (iterator.hasNext()) {
            StringBuilder next = iterator.next();
            strQuery.append(next);
            if (iterator.hasNext()) {
                strQuery.append(" AND ");
            }
        }
        strQuery.append(";");
        OCommandSQL query = new OCommandSQL(strQuery.toString());
        OCommandRequest command = graph.command(query);
        Iterable<Vertex> resultado = command.execute();
        return resultado;
    }

    private Fill getFill(Vertex vertex) {
        Fill fill = new Fill();
        this.retrievedItemsInRecursion.put(vertex, fill);
        Set<String> propertyKeys = vertex.getPropertyKeys();
        for (String propertyKey : propertyKeys) {
            Object property = vertex.getProperty(propertyKey);
            fill.addAttribute(propertyKey, property);
        }

        Iterable<Edge> edges = vertex.getEdges(Direction.OUT);
        for (Edge edge : edges) {
            Fill objectRepresentation;
            Vertex child = edge.getVertex(Direction.IN);
            if (this.retrievedItemsInRecursion.containsKey(child)) {
                objectRepresentation = this.retrievedItemsInRecursion.get(child);
            } else {
                objectRepresentation = getFill(child);
            }
            String field = edge.getLabel().replaceFirst("edge_", "");

            String vinculo = edge.getProperty("vinculo");
            switch (vinculo) {
                case "simples":
                    fill.addAttribute(field, objectRepresentation);
                    break;
                case "colecao":
                    Collection collection;
                    if (!fill.containsKey(field)) {
                        collection = new ArrayList();
                        fill.addAttribute(field, collection);
                    } else {
                        collection = (Collection) fill.getAttribute(field);
                    }
                    collection.add(objectRepresentation);
                    break;
                case "map":
                    Set<String> keyMap = edge.getPropertyKeys();
                    for (String key : keyMap) {
                        Map map;
                        if (!fill.containsKey(field)) {
                            map = new HashMap<>();
                            fill.addAttribute(field, map);
                        } else {
                            map = (Map) fill.getAttribute(field);
                        }
                        Vertex valueChild = edge.getProperty(key);
                        Fill childFill;
                        if (this.retrievedItemsInRecursion.containsKey(valueChild)) {
                            childFill = this.retrievedItemsInRecursion.get(valueChild);
                        } else {
                            childFill = getFill(valueChild);
                        }
                        map.put(objectRepresentation, childFill);
                        break;
                    }
                    break;
            }
        }
        return fill;
    }

    @Override
    public T retrieve(T t) throws ItemNotFoundException {
        synchronized (GraphPersistence.class) {
            this.retrievedItemsInRecursion.clear();
            Iterable<Vertex> resultado = retrieveVertex(t);
            for (Vertex vertex : resultado) {
                Fill fill = getFill(vertex);
                T entity = buildEntity(fill);
                entity.fillEntity(fill);
                return entity;
            }
            throw new ItemNotFoundException("Item nao encontrado");
        }
    }

    @Override
    public void update(T t) throws ItemNotFoundException {
        Iterable<Vertex> retrieveVertex = retrieveVertex(t);
        for (Vertex vertex : retrieveVertex) {
            deleteVertex(vertex);
            create(t);
            return;
        }
        throw new ItemNotFoundException();
    }

    private void deleteVertex(Vertex vertex) {
        this.deletedItemsInRecursion.add(vertex);
        Iterable<Edge> edges = vertex.getEdges(Direction.OUT);
        for (Edge edge : edges) {
            Vertex child = edge.getVertex(Direction.IN);
            if (!this.deletedItemsInRecursion.contains(child)) {
                deleteVertex(child);
            }
        }
        vertex.remove();
    }

    @Override
    public void delete(T t) throws ItemNotFoundException {
        synchronized (GraphPersistence.class) {
            deletedItemsInRecursion.clear();
            Iterable<Vertex> retrieveVertex = retrieveVertex(t);
            for (Vertex vertex : retrieveVertex) {
                deleteVertex(vertex);
                graph.commit();
                return;
            }
            throw new ItemNotFoundException();
        }
    }

}
