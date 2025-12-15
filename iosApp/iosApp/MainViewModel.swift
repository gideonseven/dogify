//
//  MainViewModel.swift
//  iosApp
//
//  Created by Gideon Tobing on 11/12/2025.
//
import Foundation
import ComposeApp
import Combine
import RxSwift
import KMPNativeCoroutinesRxSwift

class MainViewModel: ObservableObject {
    private let repository = KoinHelper.shared.getBreedsRepository()
    private let getBreeds = KoinHelper.shared.getGetBreedsUseCase()
    private let fetchBreeds = KoinHelper.shared.getFetchBreedsUseCase()
    private let onToggleFavouriteState = KoinHelper.shared.getToggleFavouriteStateUseCase()
    
    @Published
    private(set) var state = State.LOADING
    
    @Published
    var shouldFilterFavourites = false
    
    @Published
    private(set) var filteredBreeds: [Breed] = []
    
    @Published
    private var breeds: [Breed] = []
    
    private let disposeBag = DisposeBag()
    
    init() {
        createObservable(for: repository.breedsNative)
            .subscribe(onNext: { breeds in
                DispatchQueue.main.async {
                    self.breeds = breeds
                }
            }).disposed(by: disposeBag)
        
        $breeds.combineLatest($shouldFilterFavourites) { breeds, shouldFilterFavourites -> [Breed] in
            let result: [Breed]
            if shouldFilterFavourites {
                result = breeds.filter { $0.isFavourite }
            } else {
                result = breeds
            }
            
            DispatchQueue.main.async {
                self.state = result.isEmpty ? .EMPTY : .NORMAL
            }
            return result
        }.assign(to: &$filteredBreeds)
        
        getData()
    }
    
    func getData() {
        state = .LOADING
        
        createSingle(for: getBreeds.invokeNative())
            .subscribe(onSuccess: { _ in
                DispatchQueue.main.async {
                    self.state = .NORMAL
                }
            }, onFailure: { error in
                DispatchQueue.main.async {
                    self.state = .ERROR
                }
            }).disposed(by: disposeBag)
    }
    
    func fetchData() {
        state = .LOADING
        
        createSingle(for: fetchBreeds.invokeNative())
            .subscribe(onSuccess: { _ in
                DispatchQueue.main.async {
                    self.state = .NORMAL
                }
            }, onFailure: { error in
                DispatchQueue.main.async {
                    self.state = .ERROR
                }
            }).disposed(by: disposeBag)
    }
    
    func onFavouriteTapped(breed: Breed) {
        createSingle(for: onToggleFavouriteState.invokeNative(breed: breed))
            .subscribe(onFailure: { _ in
                // Ignoring failure
            }).disposed(by: disposeBag)
    }
    
    enum State {
        case NORMAL
        case LOADING
        case ERROR
        case EMPTY
    }
}
