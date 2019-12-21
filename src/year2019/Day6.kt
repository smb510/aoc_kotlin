package year2019

import base.Problem
import java.util.*

class Day6 : Problem("year2019/day6.txt"){

    val lefts = mutableSetOf<String>()
    val rights = mutableSetOf<String>()

    val tree = mutableMapOf<String, MutableList<String>>()
    val undirectedTree = mutableMapOf<String, MutableSet<String>>()

    override fun part1() {
        val lines = getLines()
        lines.forEach {
            val parts = it.split(")")
            lefts.add(parts[0])
            rights.add(parts[1])

            val children = tree.getOrElse(parts[0]) { mutableListOf()}
            children.add(parts[1])
            tree[parts[0]] = children
        }

        val roots = lefts.subtract(rights).stream().findFirst().get()
        println("Depth: ${maxDepth(roots, 0)}")
    }

    fun maxDepth(node: String, depth: Int) : Int {
        if (tree[node] == null){
            return depth
        }
        var sum = 0
        for (child in tree[node]!!) {
            sum += maxDepth(child, depth + 1)
        }
        return  sum + depth
    }

    override fun part2() {
        val lines = getLines()
        lines.forEach {
            val parts = it.split(")")
            val children = undirectedTree.getOrElse(parts[0]) { mutableSetOf()}
            children.add(parts[1])
            undirectedTree[parts[0]] = children
            val reverseChildren = undirectedTree.getOrElse(parts[1]) { mutableSetOf()}
            reverseChildren.add(parts[0])
            undirectedTree[parts[1]] = reverseChildren
        }

        println("${bfs("YOU", "SAN")}")

    }

    private fun bfs(from: String, to: String) : Int {
        val queue = mutableListOf<Pair<String, Int>>()
        val visited = mutableSetOf<String>()
        queue.add(Pair(from, 0))
        visited.add(from)
        while(queue.isNotEmpty()) {
            val node = queue.removeAt(0)
            if (node.first == to) {
                return node.second
            }
            for (neighbor in undirectedTree[node.first]!!) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor)
                    queue.add(Pair(neighbor, node.second + 1))
                }
            }
        }
        return -1
    }
}

fun main() {
    val day6 = Day6()
    day6.part2()
}