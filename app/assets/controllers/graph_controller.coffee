
class Denigma.GraphController extends Batman.Controller
  ###
    This controller contains basic grid events
  ###

  constructor: ->
    super

  @g: null
  @cont: null

  init: ->
    @cont = document.getElementById("graph")
    @g = sigma.init(@cont)

    @g.drawingProperties(
      defaultLabelColor: "#ccc"
      font: "Arial"
      edgeColor: "source"
      defaultEdgeType: "curve"
    ).graphProperties
      minNodeSize: 1
      maxNodeSize: 5

  index: ->
    #@render(false)
    @init()
    @test()

  test: ->
    hello = @g.addNode("hello",
      label: "Hello"
      x: Math.random()
      y: Math.random()
    )
    world = @g.addNode("world",
      label: "World!"
      x: Math.random()
      y: Math.random()
    )
    @g.addEdge('hello_world','hello','world').draw()

#activates method of the contoller
Denigma.root("graph#index")