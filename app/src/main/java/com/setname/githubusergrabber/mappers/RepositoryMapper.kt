package com.setname.githubusergrabber.mappers

import com.setname.githubusergrabber.entities.cache.ModelRepositoryCache
import com.setname.githubusergrabber.entities.cache.Repository
import com.setname.githubusergrabber.entities.repository.ModelRepositoryRepository

class RepositoryMapperImpl : RepositoryMapper {

    override fun convertToCacheListFromRepositoryList(listOfModels: List<ModelRepositoryRepository>): List<ModelRepositoryCache> {

        fun convert(modelRepositoryRepository: ModelRepositoryRepository): ModelRepositoryCache {
            return ModelRepositoryCache(
                modelRepositoryRepository.id,
                modelRepositoryRepository.owner.id,
                Repository(
                    modelRepositoryRepository.name,
                    modelRepositoryRepository.description ?: "",
                    modelRepositoryRepository.language ?: ""
                )
            )
        }

        return listOfModels.map { convert(it) }


    }

    override fun convertToRepositoryListFromRepository(listOfModels: List<ModelRepositoryRepository>): List<Repository> {

        fun convert(modelRepositoryRepository: ModelRepositoryRepository): Repository {

            return Repository(
                modelRepositoryRepository.name,
                modelRepositoryRepository.description ?: "",
                modelRepositoryRepository.language ?: ""
            )

        }

        return listOfModels.map { convert(it) }

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

    fun convertToRepositoryListFromRepository(listOfModels: List<ModelRepositoryRepository>): List<Repository>

    fun convertToRepositoryListFromCache(listOfModels: List<ModelRepositoryCache>): List<Repository>

    fun convertToCacheListFromRepositoryList(listOfModels: List<ModelRepositoryRepository>): List<ModelRepositoryCache>

}