package aed.graphs;

import java.util.Scanner;
import java.util.Stack;

public class MaxCycleMST {
    public class CycleDetetor {
        private boolean[] visited;
        private UndirectedWeightedGraph graph;
        private boolean hasCycle;
        private Stack<UndirectedEdge> stack;
        private UndirectedEdge maxEdge;
        private int vertice;

        public CycleDetetor(UndirectedWeightedGraph g) {
            this.graph = g;
            this.visited = new boolean[g.vCount()];
            this.stack = new Stack<UndirectedEdge>();
        }

        public boolean hasCycle() {
            return this.hasCycle;
        }

        public void search() {
            int vertices = this.graph.vCount();
            this.hasCycle = false;

            for (int i = 0; i < vertices; i++)
                this.visited[i] = false; //initialize array to false

            for (int i = 0; i < vertices; i++) { //start a new search for each vertex that has not been visited yet
                if (!this.visited[i]) visit(-1, i); // array starts 0 and never search -1
                if (this.hasCycle) break; // para quando acha um ciclo, (pode nao chegar a visitar os vertices todos)
            }

                if (!stack.isEmpty() && this.hasCycle) { // se a stack nao estao vazia e existe ciclo
                    this.maxEdge = stack.pop(); // incializa maxEdge e dou pop para comparar

                    while (!stack.isEmpty()) { //enquanto a stack nao esta vazia
                        UndirectedEdge adj = stack.pop(); // inicializo adj e dou pop para comparar
                        if (adj.compareTo(maxEdge) > 0) // se o da esquerda > que da direita da 1 senao da -1 e igual 0
                            maxEdge = adj; // guarda adj no maxEdge
                        if (vertice == adj.v1() || vertice == adj.v2()) break; // verifica se já chegamos ao fim do ciclo
                    }
                }
            }

        private void visit(int from, int v) {
            this.visited[v] = true;
            for (UndirectedEdge adj : graph.adj(v)) // itera com o graph.adj
            {
                if (this.hasCycle) return; //if a cycle was already detected we do not need to continue
                int other = adj.other(v);

                if (other != from) {   // verifica se nao volta para o vertice anterior
                    stack.push(adj);
                    if (!this.visited[other]) {
                        visit(v, other); //if a vertex was already visited
                    } else {
                        this.hasCycle = true;
                        vertice = other; // guarda incio/fim do ciclo
                        return;
                    }
                }
            }
            if (!stack.isEmpty() && !this.hasCycle) // se a stack nao estao vazia e nao existe ciclo
                stack.pop();
        }
    }

    private UndirectedWeightedGraph graph;
    private UndirectedWeightedGraph mst;
    private boolean didBuild;

    public MaxCycleMST(UndirectedWeightedGraph g)
    {
        this.graph = g;
        this.mst = new UndirectedWeightedGraph(g.vCount());
        this.didBuild = false;
    }

    public UndirectedEdge determineMaxInCycle(UndirectedWeightedGraph g)
    {
        CycleDetetor cycle = new CycleDetetor(g);
        cycle.search();
        if(!cycle.hasCycle()) // se existir um ciclo return maxEdge
            return null;
        return cycle.maxEdge;
    }

    public UndirectedWeightedGraph buildMST()
    {
        this.didBuild = true;
        for(UndirectedEdge adj : this.graph.allEdges()) { // percorre todos os arcos
            this.mst.addEdge(adj);
           UndirectedEdge result = determineMaxInCycle(this.mst);

           if(result == null) continue;
           else this.mst.removeEdge(result);
        }
        return this.mst;
    }

    public UndirectedWeightedGraph getMST()
    {
        if(didBuild == true)
            return this.mst;
        else return null;
    }


   public static void main(String[] args)
    {
        UndirectedWeightedGraph graph = UndirectedWeightedGraph.parse(new Scanner(System.in));
        MaxCycleMST g = new MaxCycleMST(graph);
        g.buildMST();
        System.out.println(g.getMST().toString());
    }
}
