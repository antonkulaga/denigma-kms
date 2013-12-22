class Denigma.LongevityController extends Batman.Controller
  ###
    This controller contains basic grid events
  ###

  s: null

  constructor: ->
    super

  @accessor 'context',
    get: -> Denigma.Vertex
    set: (_, value) -> @_v = value
    unset: -> delete @_v


  genId : ->
    ###
    ##Generates GUI as id for a record
    ###
    "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace /[xy]/g, (c) ->
      r = Math.random() * 16 | 0
      v = (if c is "x" then r else (r & 0x3 | 0x8))
      v.toString 16

  initMercury: ->
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
      floating   : true                                    # floats to the focused region
      floatWidth : false                                     # fixed width for floating interface (pixel value - eg. 520)
      floatDrag  : true
      visible    : false

    Mercury.init()

  labelSize:  12
  labelSizeOver : 16

  generateGraph: (n,e)->
    g =
      nodes: []
      edges: []
    N = n
    E = e
    for num in [0..N-1]
      g.nodes.push
        id: "n" + num
        label: "Node " + num
        x: Math.random()
        y: Math.random()
        size: @labelSize
        color: "#666"

    for num in [0..E-1]
      g.edges.push
        id: "e" + num
        source: "n" + (Math.random() * N | 0)
        target: "n" + (Math.random() * N | 0)
        size: Math.random()
        color: "#ccc"
    g

  @afterAction only: "content", ->@initGraph()


  initGraph: ->
    @s = new sigma(
      graph: @generateGraph(25,100)
      container: "graph-container"
      defaultEdgeType: "curve"
    )

    # Bind the events:
    @s.bind "overNode", (e) ->
      #e.data.node.size = @labelSizeOver
      console.log e.type, e.data.node.label

    @s.bind "outNode", (e) ->
      #e.data.node.size = @labelSize
      console.log e.type, e.data.node.label
    @s.startForceAtlas2()
    #alert "!"

  content: ->
    #@initMercury()


  showEditor:->
    Mercury.trigger('reinitialize')
    Mercury.show()

  hideEditor:->
    alert "hide"
    Mercury.hide()


  test: (one,two)->
    alert "test click"

