//
//  MainViewModel.swift
//  iosApp
//
//  Created by Gideon Tobing on 11/12/2025.
//
import Foundation
import ComposeApp

class MainViewModel: ObservableObject {
    init(){
        FetchBreedsUseCase.init().invoke {
            breeds, error in
        }
    }
}
