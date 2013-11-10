class Denigma.SectionView extends Batman.View

  ready: ->
    options =
      exclusive: false
    element = $(@get("node"))
    element.find('.accordion').accordion(options)


class Denigma.EdgeView extends Denigma.SectionView
class Denigma.VertexView extends Denigma.SectionView

class Denigma.Accordion extends Batman.View
  ready: ->
    options =
      exclusive: false
    element = $(@get("node"))

    setTimeout (->element.find('.ui.accordion').accordion(options)), 1000


class Denigma.EdgesView extends Denigma.Accordion
class Denigma.VerticesView extends Denigma.Accordion
