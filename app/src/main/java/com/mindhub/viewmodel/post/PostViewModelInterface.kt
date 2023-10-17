package com.mindhub.viewmodel.post

import com.mindhub.model.entities.Expertise

interface PostViewModelInterface {
    fun setTitle(title: String)
    fun getTitle(): String
    fun setContent(content: String)
    fun getContent(): String
    fun setExpertise(expertise: Expertise)
    fun getExpertise(): String
}