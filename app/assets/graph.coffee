###
This example is a copy of "basic.html", but with event bindings after.
Open your browser's console to see the "click", "overNode" and
"outNode" events logged.
###
i = undefined
s = undefined
N = 25
E = 100
g =
  nodes: []
  edges: []

l = 12
lb = 16
i = 0
while i < N
  g.nodes.push
    id: "n" + i
    label: "Node " + i
    x: Math.random()
    y: Math.random()
    size: l
    color: "#666"

  i++
i = 0
while i < E
  g.edges.push
    id: "e" + i
    source: "n" + (Math.random() * N | 0)
    target: "n" + (Math.random() * N | 0)
    size: Math.random()
    color: "#ccc"

  i++
s = new sigma(
  graph: g
  container: "graph-container"
  defaultEdgeType: "curve"
)

# Bind the events:
s.bind "overNode", (e) ->
  e.data.node.size = lb
  console.log e.type, e.data.node.label

s.bind "outNode", (e) ->
  e.data.node.size = l
  console.log e.type, e.data.node.label
s.startForceAtlas2()
