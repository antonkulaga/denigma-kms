class Denigma.LongevityController extends Batman.Controller
  ###
    This controller contains basic grid events
  ###

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

  content: ->
    @initMercury()

  showEditor:->
    Mercury.trigger('reinitialize')
    Mercury.show()

  hideEditor:->
    alert "hide"
    Mercury.hide()


  test: (one,two)->
    alert "test click"

