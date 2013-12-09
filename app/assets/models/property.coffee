class Denigma.Property extends Batman.Model

  serializeAsForm: false
  @serializeAsForm: false #do not remember if it is static or normal property
  @primaryKey: 'name'

  #@hasMany "members", {saveInline:false,autoload:true,inverseOf: 'organization', foreignKey:'organization'}

  #@encode 'id','type', 'value'
  @encode 'name', 'kind', "value"
  #@belongsTo 'parentItem', inverseOf: 'prop'

  @storageKey: 'properties'
  @persist Batman.RestStorage
