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
    Mercury.configuration.interface =
      enabled    : true                                     # initial visible state - trigger 'interface:show' to show
      class      : 'FrameInterface'                          # interface class - used on Mercury.init()
      toolbar    : 'Toolbar'                                 # toolbar class to use within the interface
      statusbar  : 'Statusbar'                               # statusbar class to use within the interface
      uploader   : 'Uploader'                                # uploader class to use within the interface
      silent     : false                                     # set to true to disable asking about changes when leaving
      shadowed   : true                                     # puts the interface into a shadow dom when it's available
      maskable   : false                                     # uses a mask over the document for toolbar dialogs
      style      : false                                     # interface style - 'small', 'flat' or 'small flat'
      floating   : true                                     # floats to the focused region
      floatWidth : false                                     # fixed width for floating interface (pixel value - eg. 520)
      floatDrag  : true
    Mercury.init()
    #Mercury.hide()
    #@render(false)
    #@init()
    #@test()

  showEditor:->
    alert "werfg"
    #Mercury.show()

  hideEditor:->
    #Mercury.hide()


  @accessor 'v',
    get: -> if @_v? then @_v else "testRoot"
    set: (_, value) -> @_v = value
    unset: -> delete @_v

  @accessor 'inE', ->
    id = "testRoot"
    unless @il then Denigma.Edge.loadWithOptions({url:"incoming/#{id}"})
    #fun = (f)->f.get("outgoing").some((v)->v.id==id)
    fun = (f)->true
    @il = true
    Denigma.Edge.get('loaded')

  ol:false
  il:false

  @accessor 'outE', ->
    id = "testRoot"
    unless @ol then Denigma.Edge.loadWithOptions({url:"outgoing/#{id}"})
    @ol = true
    #fun = (f)->f.get("outgoing").some((v)->v.id==id)
    fun = (f)->true

    Denigma.Edge.get('some')

  add: ->
    props =
      key1:"value1"
      key2:"value2"
      key3:"value3"

    n = new Denigma.Vertex(id:@genId(),name:"test_name2", description:"test_description2",properties:props)
    n.save()

  testClick: (one,two)->
    vs = Denigma.Vertex.get("loaded").toArray()[0]
    ps = vs.get("properties").toJSON
    debugger
    alert JSON.stringify(vs)
    alert JSON.stringify(ps)

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

