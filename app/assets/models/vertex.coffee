class Denigma.Vertex extends Batman.Model

  serializeAsForm: false
  @serializeAsForm: false #do not remember if it is static or normal property
  @primaryKey: 'id'
  #@hasMany "members", {saveInline:false,autoload:true,inverseOf: 'organization', foreignKey:'organization'}

  @hasMany "properties", {encoderKey: 'properties',saveInline:true,autoload:false}

  @encode 'id','name','description'

  #@hasMany "edges", inverseOf: "edge"
  #@persist Batman.LocalStorage

  @storageKey: 'vertices'
  @persist Batman.RestStorage
