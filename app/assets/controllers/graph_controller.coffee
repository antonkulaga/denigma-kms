class Denigma.GraphController extends Batman.Controller
  ###
    This controller contains basic grid events
  ###

  constructor: ->
    super

  #@accessor("items",->Denigma.Node.get("all"))

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

  genId : ->
      ###
      ##Generates GUI as id for a record
      ###
    "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace /[xy]/g, (c) ->
      r = Math.random() * 16 | 0
      v = (if c is "x" then r else (r & 0x3 | 0x8))
      v.toString 16

  index: ->
    #@render(false)
    @init()
    @test()



  add: ->
    props =
      key1:"value1"
      key2:"value2"
      key3:"value3"

    n = new Denigma.Vertex(id:@genId(),name:"test_name2", description:"test_description2",properties:props)
    n.save()


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