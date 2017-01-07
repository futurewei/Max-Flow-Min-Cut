import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import cs_1c.*;

public class Foothill
{
   // ------- main --------------
   public static void main(String[] args) throws Exception
   {
      double finalFlow;
      // first test case -----
      System.out.println("----Test 1----");
      // build graph
      FHflowGraph<String> myG = new FHflowGraph<String>();

      myG.addEdge("s", "a", 3);
      myG.addEdge("s", "b", 2);
      myG.addEdge("a", "b", 1);
      myG.addEdge("a", "c", 3);
      myG.addEdge("a", "d", 4);
      myG.addEdge("b", "d", 2);
      myG.addEdge("c", "t", 2);
      myG.addEdge("d", "t", 3);

      // show the original flow graph
      myG.showResAdjTable();
      myG.showFlowAdjTable();

      myG.setStartVert("s");
      myG.setEndVert("t");
      finalFlow = myG.findMaxFlow();

      System.out.println("Final flow: " + finalFlow);

      myG.showResAdjTable();
      myG.showFlowAdjTable();

      // second test case--------------
      System.out.println("--------Test2------");
      myG.clear();
      myG.addEdge("s", "a", 4);
      myG.addEdge("s", "b", 2);
      myG.addEdge("a", "b", 1);
      myG.addEdge("a", "c", 2);
      myG.addEdge("a", "d", 4);
      myG.addEdge("b", "d", 2);
      myG.addEdge("c", "t", 3);
      myG.addEdge("d", "t", 3);

      // show the original flow graph
      myG.showResAdjTable();
      myG.showFlowAdjTable();

      myG.setStartVert("s");
      myG.setEndVert("t");
      finalFlow = myG.findMaxFlow();

      System.out.println("Final flow: " + finalFlow);

      myG.showResAdjTable();
      myG.showFlowAdjTable();

      // third test case----------------
      System.out.println("--------Test3------");
      myG.clear();
      myG.addEdge("s", "A", 1);
      myG.addEdge("s", "D", 4);
      myG.addEdge("s", "G", 6);
      myG.addEdge("A", "B", 2);
      myG.addEdge("A", "E", 2);
      myG.addEdge("B", "C", 2);
      myG.addEdge("C", "t", 4);
      myG.addEdge("D", "E", 3);
      myG.addEdge("D", "A", 3);
      myG.addEdge("E", "C", 2);
      myG.addEdge("E", "F", 3);
      myG.addEdge("E", "I", 3);
      myG.addEdge("F", "C", 1);
      myG.addEdge("F", "t", 3);
      myG.addEdge("G", "D", 2);
      myG.addEdge("G", "E", 1);
      myG.addEdge("G", "H", 6);
      myG.addEdge("H", "E", 2);
      myG.addEdge("H", "I", 6);
      myG.addEdge("I", "F", 1);
      myG.addEdge("I", "t", 4);

      // show the original flow graph
      myG.showResAdjTable();
      myG.showFlowAdjTable();

      myG.setStartVert("s");
      myG.setEndVert("t");
      finalFlow = myG.findMaxFlow();

      System.out.println("Final flow: " + finalFlow);

      myG.showResAdjTable();
      myG.showFlowAdjTable();

      // // fourth test case-----------------------------
      System.out.println("--------Test4------");
      myG.clear();

      myG.addEdge("s", "h", 5);
      myG.addEdge("h", "i", 5);
      myG.addEdge("i", "b", 5);
      myG.addEdge("a", "b", 5);
      myG.addEdge("s", "a", 5);
      myG.addEdge("s", "c", 5);
      myG.addEdge("s", "d", 5);
      myG.addEdge("a", "e", 5);
      myG.addEdge("e", "j", 5);
      myG.addEdge("j", "t", 5);
      myG.addEdge("d", "g", 5);
      myG.addEdge("g", "t", 5);
      myG.addEdge("b", "t", 5);
      myG.addEdge("c", "t", 5);
      myG.addEdge("d", "c", 5);

      // show the original flow graph
      myG.showResAdjTable();
      myG.showFlowAdjTable();

      myG.setStartVert("s");
      myG.setEndVert("t");
      finalFlow = myG.findMaxFlow();

      System.out.println("Final flow: " + finalFlow);

      myG.showResAdjTable();
      myG.showFlowAdjTable();
   }
}

class FHflowGraph<E>
{
   // the graph data is all here --------------------------
   protected HashSet<FHflowVertex<E>> vertexSet;
   FHflowVertex<E> startVert, endVert;

   // public graph methods --------------------------------
   public FHflowGraph()
   {
      vertexSet = new HashSet<FHflowVertex<E>>();
      startVert = null;
      endVert = null;
   }

   public void addEdge(E source, E dest, double cost)
   {
      FHflowVertex<E> src, dst;

      // put both source and dest into vertex list(s) if not already there
      src = addToVertexSet(source);
      dst = addToVertexSet(dest);

      // add destination to source's adjacency list
      src.addToResAdjList(dst, cost);
      dst.addToResAdjList(src, 0);// a reverse edge with cost 0
      src.addToFlowAdjList(dst, 0); // construct a flow graph with all 0 cost
   }

   public void addEdge(E source, E dest, int cost)
   {
      addEdge(source, dest, (double) cost);
   }

   // adds vertex with x in it, and always returns ref to it
   public FHflowVertex<E> addToVertexSet(E x)
   {
      FHflowVertex<E> retVal, vert;
      boolean successfulInsertion;
      Iterator<FHflowVertex<E>> iter;

      // save sort key for client
      FHflowVertex.pushKeyType();
      FHflowVertex.setKeyType(FHflowVertex.KEY_ON_DATA);

      // build and insert vertex into master list
      retVal = new FHflowVertex<E>(x);
      successfulInsertion = vertexSet.add(retVal);

      if (successfulInsertion)
      {
         FHflowVertex.popKeyType(); // restore client sort key
         return retVal;
      }

      // the vertex was already in the set, so get its ref
      for (iter = vertexSet.iterator(); iter.hasNext();)
      {
         vert = iter.next();
         if (vert.equals(retVal))
         {
            FHflowVertex.popKeyType(); // restore client sort key
            return vert;
         }
      }

      FHflowVertex.popKeyType(); // restore client sort key
      return null; // should never happen
   }

   public void showFlowAdjTable()
   {
      Iterator<FHflowVertex<E>> iter;

      System.out.println("------------------------ ");
      for (iter = vertexSet.iterator(); iter.hasNext();)
         (iter.next()).showFlowAdjList();
      System.out.println();
   }

   public void showResAdjTable()
   {
      Iterator<FHflowVertex<E>> iter;

      System.out.println("------------------------ ");
      for (iter = vertexSet.iterator(); iter.hasNext();)
         (iter.next()).showResAdjList();
      System.out.println();
   }

   @SuppressWarnings("unchecked")
   public HashSet<FHflowVertex<E>> getVertSet()
   {
      return (HashSet<FHflowVertex<E>>) vertexSet.clone();
   }

   // as discussed in forum, clear should be modified to set start, end to null
   public void clear()
   {
      vertexSet.clear();
      startVert = null;
      endVert = null;
   }

   // new added methods:
   public boolean setStartVert(E x)
   {
      startVert = addToVertexSet(x);
      return true;
   }

   public boolean setEndVert(E x)
   {
      endVert = addToVertexSet(x);
      return true;
   }

   // main algorithm of max flow, returns the max flow found
   public double findMaxFlow()
   {
      double limitingflow;
      double maxFlow = 0.0;
      Iterator<Pair<FHflowVertex<E>, Double>> iter;
      // loop to get paths, until there are no more paths from start to end
      while (establishNextFlowPath())
      {
         limitingflow = getLimitingFlowOnResPath();
         // adjust graphs every time we get a path
         adjustPathByCost(limitingflow);
      }
      if (startVert != null && endVert != null)
      {
         // summing all out-flowing costs from start vertex in flow Graph
         for (iter = startVert.flowAdjList.iterator(); iter.hasNext();)
         {
            maxFlow = iter.next().second + maxFlow;
         }
      }
      return maxFlow;
   }

   // dijkstra based method, helps find paths.
   protected boolean establishNextFlowPath()
   {
      FHflowVertex<E> w, v;
      Pair<FHflowVertex<E>, Double> edge;
      Iterator<FHflowVertex<E>> iter;
      Iterator<Pair<FHflowVertex<E>, Double>> edgeIter;
      Double costVW;
      Deque<FHflowVertex<E>> partiallyProcessedVerts = new 
            LinkedList<FHflowVertex<E>>();

      if (startVert == null || endVert == null)
         return false;

      // initialize the vertex list and place the starting vert in p_p_v queue
      for (iter = vertexSet.iterator(); iter.hasNext();)
         iter.next().dist = FHflowVertex.INFINITY;

      // always start at startVert
      startVert.dist = 0;
      partiallyProcessedVerts.addLast(startVert);

      // outer dijkstra loop
      while (!partiallyProcessedVerts.isEmpty())
      {
         v = partiallyProcessedVerts.removeFirst();

         // inner dijkstra loop: for each vert adj to v, lower its dist
         // to s if you can
         for (edgeIter = v.resAdjList.iterator(); edgeIter.hasNext();)
         {
            edge = edgeIter.next();
            w = edge.first;
            costVW = edge.second;
            // if costVw==0, skip it
            if (costVW != 0)
            {
               if (v.dist + costVW < w.dist)
               {
                  w.dist = v.dist + costVW;
                  w.nextInPath = v;
                  // w now has improved distance, so add w to PPV queue
                  partiallyProcessedVerts.addLast(w);
                  if (w.equals(endVert))
                     return true;
               }
            }
         }
      }
      // return false if no path can reach the end
      return false;
   }

   // protected graph methods --------------------------
   // helper method for findMaxFlow(). Return the limiting flow("bottle neck")
   // on each path found.
   protected double getLimitingFlowOnResPath()
   {
      FHflowVertex<E> vert;
      double currentEdgeCost;
      // initialize limiting flow as the cost between the end vertex's previous
      // vertex and the end vertex
      double limitingFlow = getCostOfResEdge(endVert.nextInPath, endVert);

      // trace back
      for (vert = endVert; vert != startVert; vert = vert.nextInPath)
      {
         currentEdgeCost = getCostOfResEdge(vert.nextInPath, vert);
         // compare the edges
         if (currentEdgeCost < limitingFlow)
            limitingFlow = currentEdgeCost;
      }
      return limitingFlow;
   }

   protected boolean adjustPathByCost(double cost)
   {
      FHflowVertex<E> vert;

      for (vert = endVert; vert != startVert; vert = vert.nextInPath)
      {
         // add cost to edge in flow graph
         addCostToFlowEdge(vert.nextInPath, vert, cost);
         // add&subtract cost to/from edge in residual graph
         addCostToResEdge(vert, vert.nextInPath, cost);
      }
      // return value is not important here since establishpath method would
      // check everything, and return false, when needy, to terminates loop
      // without using adjustPathByCost()
      return true;
   }

   // helper method for getLimitingFlowOnResPath()
   protected double getCostOfResEdge(FHflowVertex<E> src, FHflowVertex<E> dst)
   {
      Iterator<Pair<FHflowVertex<E>, Double>> iter;
      Pair<FHflowVertex<E>, Double> neighbor;
      // exam src's adjacency list to get the destination's cost
      for (iter = src.resAdjList.iterator(); iter.hasNext();)
      {
         neighbor = iter.next();
         if (dst.equals(neighbor.first))
         {
            return neighbor.second;
         }
      }
      return 0.0;
   }

   // a method that adds/subtracts cost to residual edge in both direction
   protected boolean addCostToResEdge(FHflowVertex<E> src, FHflowVertex<E> dst,
         double cost)
   {
      Iterator<Pair<FHflowVertex<E>, Double>> iter;
      Pair<FHflowVertex<E>, Double> neighbor;

      // exam src's adjacency list to find dst
      for (iter = src.resAdjList.iterator(); iter.hasNext();)
      {
         neighbor = iter.next();
         if (dst.equals(neighbor.first))
         {
            // add cost to the reverse edge
            neighbor.second = neighbor.second + cost;
            break;
         }
      }
      // exam dst's adjacency list to find src
      for (iter = dst.resAdjList.iterator(); iter.hasNext();)
      {
         neighbor = iter.next();
         if (src.equals(neighbor.first))
         {
            // add negative cost to the edge=subtract
            neighbor.second = neighbor.second - cost;
            return true;
         }
      }
      return false;
   }

   protected boolean addCostToFlowEdge(FHflowVertex<E> src,
         FHflowVertex<E> dst, double cost)
   {
      Iterator<Pair<FHflowVertex<E>, Double>> iter;
      Pair<FHflowVertex<E>, Double> neighbor;

      // exam src's adjacency list to find dst
      for (iter = src.flowAdjList.iterator(); iter.hasNext();)
      {
         neighbor = iter.next();
         if (dst.equals(neighbor.first))
         {
            neighbor.second = neighbor.second + cost;
            return true;
         }
      }
      // dst not found, edge passed in was a reverse order
      for (iter = dst.flowAdjList.iterator(); iter.hasNext();)
      {
         neighbor = iter.next();
         if (src.equals(neighbor.first))
         {
            neighbor.second = neighbor.second - cost;
            return true;
         }
      }
      return false;
   }

   protected FHflowVertex<E> getVertexWithThisData(E x)
   {
      FHflowVertex<E> searchVert, vert;
      Iterator<FHflowVertex<E>> iter;

      // save sort key for client
      FHflowVertex.pushKeyType();
      FHflowVertex.setKeyType(FHflowVertex.KEY_ON_DATA);

      // build vertex with data = x for the search
      searchVert = new FHflowVertex<E>(x);

      // the vertex was already in the set, so get its ref
      for (iter = vertexSet.iterator(); iter.hasNext();)
      {
         vert = iter.next();
         if (vert.equals(searchVert))
         {
            FHflowVertex.popKeyType();
            return vert;
         }
      }
      FHflowVertex.popKeyType();
      return null; // not found
   }
}

// vertex class
class FHflowVertex<E>
{
   public static Stack<Integer> keyStack = new Stack<Integer>();
   public static final int KEY_ON_DATA = 0, KEY_ON_DIST = 1;
   public static int keyType = KEY_ON_DATA;
   public static final double INFINITY = Double.MAX_VALUE;
   public HashSet<Pair<FHflowVertex<E>, Double>> flowAdjList = new 
         HashSet<Pair<FHflowVertex<E>, Double>>();
   public HashSet<Pair<FHflowVertex<E>, Double>> resAdjList = new 
         HashSet<Pair<FHflowVertex<E>, Double>>();
   public E data;
   public double dist;
   public FHflowVertex<E> nextInPath; // for client-specific info

   public FHflowVertex(E x)
   {
      data = x;
      dist = INFINITY;
      nextInPath = null;
   }

   public FHflowVertex()
   {
      this(null);
   }

   public void addToFlowAdjList(FHflowVertex<E> neighbor, double cost)
   {
      flowAdjList.add(new Pair<FHflowVertex<E>, Double>(neighbor, cost));
   }

   public void addToResAdjList(FHflowVertex<E> neighbor, double cost)
   {
      resAdjList.add(new Pair<FHflowVertex<E>, Double>(neighbor, cost));
   }

   public void addToFlowAdjList(FHflowVertex<E> neighbor, int cost)
   {
      addToFlowAdjList(neighbor, (double) cost);
   }

   public void addToResAdjList(FHflowVertex<E> neighbor, int cost)
   {
      addToResAdjList(neighbor, (double) cost);
   }

   public boolean equals(Object rhs)
   {
      @SuppressWarnings("unchecked")
      FHflowVertex<E> other = (FHflowVertex<E>) rhs;
      switch (keyType)
      {
      case KEY_ON_DIST:
         return (dist == other.dist);
      case KEY_ON_DATA:
         return (data.equals(other.data));
      default:
         return (data.equals(other.data));
      }
   }

   public int hashCode()
   {
      switch (keyType)
      {
      case KEY_ON_DIST:
         Double d = dist;
         return (d.hashCode());
      case KEY_ON_DATA:
         return (data.hashCode());
      default:
         return (data.hashCode());
      }
   }

   public void showResAdjList()
   {
      Iterator<Pair<FHflowVertex<E>, Double>> iter;
      Pair<FHflowVertex<E>, Double> pair;

      System.out.print("Residual Adj List for " + data + ": ");
      for (iter = resAdjList.iterator(); iter.hasNext();)
      {
         pair = iter.next();
         System.out.print(pair.first.data + "("
               + String.format("%3.1f", pair.second) + ") ");
      }
      System.out.println();
   }

   public void showFlowAdjList()
   {
      Iterator<Pair<FHflowVertex<E>, Double>> iter;
      Pair<FHflowVertex<E>, Double> pair;

      System.out.print("Flow Adj List for " + data + ": ");
      for (iter = flowAdjList.iterator(); iter.hasNext();)
      {
         pair = iter.next();
         System.out.print(pair.first.data + "("
               + String.format("%3.1f", pair.second) + ") ");
      }
      System.out.println();
   }

   public static boolean setKeyType(int whichType)
   {
      switch (whichType)
      {
      case KEY_ON_DATA:
      case KEY_ON_DIST:
         keyType = whichType;
         return true;
      default:
         return false;
      }
   }

   public static void pushKeyType()
   {
      keyStack.push(keyType);
   }

   public static void popKeyType()
   {
      keyType = keyStack.pop();
   };
}

/*-----------------------------Run-----------------
----Test 1----
------------------------ 
Residual Adj List for d: t(3.0) b(0.0) a(0.0) 
Residual Adj List for t: d(0.0) c(0.0) 
Residual Adj List for b: d(2.0) s(0.0) a(0.0) 
Residual Adj List for s: b(2.0) a(3.0) 
Residual Adj List for c: t(2.0) a(0.0) 
Residual Adj List for a: d(4.0) b(1.0) s(0.0) c(3.0) 

------------------------ 
Flow Adj List for d: t(0.0) 
Flow Adj List for t: 
Flow Adj List for b: d(0.0) 
Flow Adj List for s: b(0.0) a(0.0) 
Flow Adj List for c: t(0.0) 
Flow Adj List for a: d(0.0) b(0.0) c(0.0) 

Final flow: 5.0
------------------------ 
Residual Adj List for d: t(0.0) b(2.0) a(1.0) 
Residual Adj List for t: d(3.0) c(2.0) 
Residual Adj List for b: d(0.0) s(2.0) a(0.0) 
Residual Adj List for s: b(0.0) a(0.0) 
Residual Adj List for c: t(0.0) a(2.0) 
Residual Adj List for a: d(3.0) b(1.0) s(3.0) c(1.0) 

------------------------ 
Flow Adj List for d: t(3.0) 
Flow Adj List for t: 
Flow Adj List for b: d(2.0) 
Flow Adj List for s: b(2.0) a(3.0) 
Flow Adj List for c: t(2.0) 
Flow Adj List for a: d(1.0) b(0.0) c(2.0) 

--------Test2------
------------------------ 
Residual Adj List for d: t(3.0) b(0.0) a(0.0) 
Residual Adj List for t: d(0.0) c(0.0) 
Residual Adj List for b: d(2.0) s(0.0) a(0.0) 
Residual Adj List for s: b(2.0) a(4.0) 
Residual Adj List for c: t(3.0) a(0.0) 
Residual Adj List for a: d(4.0) b(1.0) s(0.0) c(2.0) 

------------------------ 
Flow Adj List for d: t(0.0) 
Flow Adj List for t: 
Flow Adj List for b: d(0.0) 
Flow Adj List for s: b(0.0) a(0.0) 
Flow Adj List for c: t(0.0) 
Flow Adj List for a: d(0.0) b(0.0) c(0.0) 

Final flow: 5.0
------------------------ 
Residual Adj List for d: t(0.0) b(2.0) a(1.0) 
Residual Adj List for t: d(3.0) c(2.0) 
Residual Adj List for b: d(0.0) s(2.0) a(0.0) 
Residual Adj List for s: b(0.0) a(1.0) 
Residual Adj List for c: t(1.0) a(2.0) 
Residual Adj List for a: d(3.0) b(1.0) s(3.0) c(0.0) 

------------------------ 
Flow Adj List for d: t(3.0) 
Flow Adj List for t: 
Flow Adj List for b: d(2.0) 
Flow Adj List for s: b(2.0) a(3.0) 
Flow Adj List for c: t(2.0) 
Flow Adj List for a: d(1.0) b(0.0) c(2.0) 

--------Test3------
------------------------ 
Residual Adj List for D: E(3.0) G(0.0) s(0.0) A(3.0) 
Residual Adj List for E: D(0.0) F(3.0) G(0.0) A(0.0) C(2.0) H(0.0) I(3.0) 
Residual Adj List for F: E(0.0) t(3.0) C(1.0) I(0.0) 
Residual Adj List for t: F(0.0) C(0.0) I(0.0) 
Residual Adj List for G: D(2.0) E(1.0) s(0.0) H(6.0) 
Residual Adj List for s: D(4.0) G(6.0) A(1.0) 
Residual Adj List for A: D(0.0) E(2.0) s(0.0) B(2.0) 
Residual Adj List for B: A(0.0) C(2.0) 
Residual Adj List for C: E(0.0) F(0.0) t(4.0) B(0.0) 
Residual Adj List for H: E(2.0) G(0.0) I(6.0) 
Residual Adj List for I: E(0.0) F(1.0) t(4.0) H(0.0) 

------------------------ 
Flow Adj List for D: E(0.0) A(0.0) 
Flow Adj List for E: F(0.0) C(0.0) I(0.0) 
Flow Adj List for F: t(0.0) C(0.0) 
Flow Adj List for t: 
Flow Adj List for G: D(0.0) E(0.0) H(0.0) 
Flow Adj List for s: D(0.0) G(0.0) A(0.0) 
Flow Adj List for A: E(0.0) B(0.0) 
Flow Adj List for B: C(0.0) 
Flow Adj List for C: t(0.0) 
Flow Adj List for H: E(0.0) I(0.0) 
Flow Adj List for I: F(0.0) t(0.0) 

Final flow: 11.0
------------------------ 
Residual Adj List for D: E(0.0) G(2.0) s(4.0) A(0.0) 
Residual Adj List for E: D(3.0) F(0.0) G(1.0) A(2.0) C(0.0) H(2.0) I(0.0) 
Residual Adj List for F: E(3.0) t(0.0) C(1.0) I(0.0) 
Residual Adj List for t: F(3.0) C(4.0) I(4.0) 
Residual Adj List for G: D(0.0) E(0.0) s(6.0) H(3.0) 
Residual Adj List for s: D(0.0) G(0.0) A(0.0) 
Residual Adj List for A: D(3.0) E(0.0) s(1.0) B(0.0) 
Residual Adj List for B: A(2.0) C(0.0) 
Residual Adj List for C: E(2.0) F(0.0) t(0.0) B(2.0) 
Residual Adj List for H: E(0.0) G(3.0) I(5.0) 
Residual Adj List for I: E(3.0) F(1.0) t(0.0) H(1.0) 

------------------------ 
Flow Adj List for D: E(3.0) A(3.0) 
Flow Adj List for E: F(3.0) C(2.0) I(3.0) 
Flow Adj List for F: t(3.0) C(0.0) 
Flow Adj List for t: 
Flow Adj List for G: D(2.0) E(1.0) H(3.0) 
Flow Adj List for s: D(4.0) G(6.0) A(1.0) 
Flow Adj List for A: E(2.0) B(2.0) 
Flow Adj List for B: C(2.0) 
Flow Adj List for C: t(4.0) 
Flow Adj List for H: E(2.0) I(1.0) 
Flow Adj List for I: F(0.0) t(4.0) 

--------Test4------
------------------------ 
Residual Adj List for g: d(0.0) t(5.0) 
Residual Adj List for d: g(5.0) s(0.0) c(5.0) 
Residual Adj List for t: g(0.0) b(0.0) c(0.0) j(0.0) 
Residual Adj List for e: a(0.0) j(5.0) 
Residual Adj List for b: t(5.0) a(0.0) i(0.0) 
Residual Adj List for s: d(5.0) c(5.0) a(5.0) h(5.0) 
Residual Adj List for c: d(0.0) t(5.0) s(0.0) 
Residual Adj List for a: e(5.0) s(0.0) b(5.0) 
Residual Adj List for j: t(5.0) e(0.0) 
Residual Adj List for h: s(0.0) i(5.0) 
Residual Adj List for i: b(5.0) h(0.0) 

------------------------ 
Flow Adj List for g: t(0.0) 
Flow Adj List for d: g(0.0) c(0.0) 
Flow Adj List for t: 
Flow Adj List for e: j(0.0) 
Flow Adj List for b: t(0.0) 
Flow Adj List for s: d(0.0) c(0.0) a(0.0) h(0.0) 
Flow Adj List for c: t(0.0) 
Flow Adj List for a: e(0.0) b(0.0) 
Flow Adj List for j: t(0.0) 
Flow Adj List for h: i(0.0) 
Flow Adj List for i: b(0.0) 

Final flow: 20.0
------------------------ 
Residual Adj List for g: d(5.0) t(0.0) 
Residual Adj List for d: g(0.0) s(5.0) c(5.0) 
Residual Adj List for t: g(5.0) b(5.0) c(5.0) j(5.0) 
Residual Adj List for e: a(5.0) j(0.0) 
Residual Adj List for b: t(0.0) a(0.0) i(5.0) 
Residual Adj List for s: d(0.0) c(0.0) a(0.0) h(0.0) 
Residual Adj List for c: d(0.0) t(0.0) s(5.0) 
Residual Adj List for a: e(0.0) s(5.0) b(5.0) 
Residual Adj List for j: t(0.0) e(5.0) 
Residual Adj List for h: s(5.0) i(0.0) 
Residual Adj List for i: b(0.0) h(5.0) 

------------------------ 
Flow Adj List for g: t(5.0) 
Flow Adj List for d: g(5.0) c(0.0) 
Flow Adj List for t: 
Flow Adj List for e: j(5.0) 
Flow Adj List for b: t(5.0) 
Flow Adj List for s: d(5.0) c(5.0) a(5.0) h(5.0) 
Flow Adj List for c: t(5.0) 
Flow Adj List for a: e(5.0) b(0.0) 
Flow Adj List for j: t(5.0) 
Flow Adj List for h: i(5.0) 
Flow Adj List for i: b(5.0) 
------------------------------------------------*/
