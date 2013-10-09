###
  #Denigma chat App#
  Application coffee of the chat
###

#= extern Batman.App
#= extern Batman.Object

#disable routingkey warnings for controllers
Batman.config.minificationErrors = false

class Denigma extends Batman.App
  #@route

  ###
  Application object of the chat
  ###


  #stores to global container
container = Batman.container
container.Denigma = Denigma

Batman.config =
  pathToApp: '/'
  usePushState: true

  pathToHTML: ''
  fetchRemoteHTML: true
  cacheViews: false

  minificationErrors: false
  protectFromCSRF: false

  #add listener to the window object to fire run when everything has been loaded
if(window?)
  window.addEventListener 'load', ->
    #disp = new Batman.EmptyDispatcher()
    #Denigma.set "navigator", disp
    #Denigma.set "dispatcher", disp
    Denigma.run()
    Denigma.fire "start"