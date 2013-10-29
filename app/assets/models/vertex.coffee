class Denigma.Vertex extends Batman.Model

  serializeAsForm: false
  @serializeAsForm: false #do not remember if it is static or normal property
  @primaryKey: 'id'
  #@hasMany "members", {saveInline:false,autoload:true,inverseOf: 'organization', foreignKey:'organization'}

  @encode 'properties',
    decode: (hash, key, incomingJSON, outgoingObject, record) ->
      props = record.getOrSet("properties",->new Batman.Hash())
      for k,v of hash
        unless (props.hasKey(k) and props.get(k)==v) then props.add(k,v)
      props
    encode: (hash) ->
      if hash.toObject? then  hash.toObject() else hash

  @encode 'id','name','description', 'properties'
  @persist Batman.LocalStorage

  @storageKey: 'vertex'
  #@persist Batman.RestStorage
