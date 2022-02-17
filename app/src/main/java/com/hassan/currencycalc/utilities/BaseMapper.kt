package com.hassan.currencycalc.utilities

//generic mapper interface
interface BaseMapper<O, E> {

    //map dto to entity
    fun mapToEntity(type: O): E

    //map entity to dto
    fun mapToDTO (type: E): O

    //map list of dto to list of entities
    fun mapToEntitiesList(objects: List<O>): List<E> {
        return objects.map { dto ->
            mapToEntity(dto)
        }
    }

    //map list of entities to list of dto
    fun mapToDTOList(entities: List<E>): List<O> {
        return entities.map { entity ->
            mapToDTO(entity)
        }
    }
}