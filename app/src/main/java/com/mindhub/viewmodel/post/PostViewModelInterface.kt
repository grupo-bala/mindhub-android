package com.mindhub.viewmodel.post

import com.mindhub.model.entities.Expertise

interface PostViewModelInterface {
    var title: String
    var content: String
    var expertise: Expertise
}