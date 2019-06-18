package com.setname.githubusergrabber.domain.mappers

import com.setname.githubusergrabber.entities.cache.ModelRepositoryCache
import com.setname.githubusergrabber.entities.cache.Repository
import com.setname.githubusergrabber.entities.repository.ModelRepositoryRemote

class RepositoryMapperImpl : RepositoryMapper {

    override fun convertToCacheListFromRepositoryList(listOfModelRemotes: List<ModelRepositoryRemote>): List<ModelRepositoryCache> {

        fun convert(modelRepositoryRemote: ModelRepositoryRemote): ModelRepositoryCache {
            return ModelRepositoryCache(
                modelRepositoryRemote.id,
                modelRepositoryRemote.owner.id,
                Repository(
                    modelRepositoryRemote.name,
                    modelRepositoryRemote.description ?: "Empty description",
                    modelRepositoryRemote.language ?: "No language selected"
                )
            )
        }

        return listOfModelRemotes.map { convert(it) }


    }

    override fun convertToRepositoryListFromRepository(listOfModelRemotes: List<ModelRepositoryRemote>): List<Repository> {

        fun convert(modelRepositoryRemote: ModelRepositoryRemote): Repository {

            return Repository(
                modelRepositoryRemote.name,
                modelRepositoryRemote.description ?: "Empty description",
                modelRepositoryRemote.language ?: "No language selected"
            )

        }

        return listOfModelRemotes.map { convert(it) }

    }

    override fun convertToRepositoryListFromCache(listOfModels: List<ModelRepositoryCache>): List<Repository> {

        fun convert(modelRepositoryCache: ModelRepositoryCache): Repository {
            return Repository(
                modelRepositoryCache.repository.name,
                modelRepositoryCache.repository.description,
                modelRepositoryCache.repository.language
            )
        }

        return listOfModels.map { convert(it) }

    }


}

interface RepositoryMapper {

    fun convertToRepositoryListFromRepository(listOfModelRemotes: List<ModelRepositoryRemote>): List<Repository>

    fun convertToRepositoryListFromCache(listOfModels: List<ModelRepositoryCache>): List<Repository>

    fun convertToCacheListFromRepositoryList(listOfModelRemotes: List<ModelRepositoryRemote>): List<ModelRepositoryCache>

}