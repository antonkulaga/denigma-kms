cont = document.getElementById("graph")
sigInst = sigma.init(cont)
sigInst.addNode("hello",
  label: "Hello"
  x: Math.random()
  y: Math.random()
).addNode("world",
  label: "World!"
  x: Math.random()
  y: Math.random()
).addEdge "hello", "world"
sigInst.drawingProperties(defaultEdgeType: "curve").mouseProperties maxRatio: 32