//
//  MainViewModel.swift
//  iosApp
//
//  Created by Gideon Tobing on 11/12/2025.
//
import Foundation
import RxSwift
import KMPNativeCoroutinesRxSwift
import ComposeApp

final class MainViewModel: ObservableObject {

    private let repository = BreedsRepository()
    private let getBreeds = GetBreedsUseCase()
    private let fetchBreeds = FetchBreedsUseCase()
    private let onToggleFavouriteState = ToggleFavouriteStateUseCase()

    @Published private(set) var state: State = .LOADING
    @Published var shouldFilterFavourites: Bool = false {
        didSet { applyFilter() }
    }
    @Published private(set) var filteredBreeds: [Breed] = []

    private var breeds: [Breed] = [] {
        didSet { applyFilter() }
    }

    private let disposeBag = DisposeBag()

    init() {
        KMPNativeCoroutinesRxSwift.createObservable(for: repository.breedsNative)
            .observe(on: MainScheduler.instance)
            .subscribe(onNext: { [weak self] breeds in
                self?.breeds = breeds
            })
            .disposed(by: disposeBag)

        getData()
    }

    private func applyFilter() {
        let result = shouldFilterFavourites
            ? breeds.filter { $0.isFavourite }
            : breeds

        filteredBreeds = result
        state = result.isEmpty ? .EMPTY : .NORMAL
    }

    func getData() {
        state = .LOADING
        KMPNativeCoroutinesRxSwift.createSingle(for: getBreeds.invokeNative())
            .observe(on: MainScheduler.instance)
            .subscribe(
                onSuccess: { [weak self] _ in self?.state = .NORMAL },
                onFailure: { [weak self] _ in self?.state = .ERROR }
            )
            .disposed(by: disposeBag)
    }

    func fetchData() {
        state = .LOADING
        KMPNativeCoroutinesRxSwift.createSingle(for: fetchBreeds.invokeNative())
            .observe(on: MainScheduler.instance)
            .subscribe(
                onSuccess: { [weak self] _ in self?.state = .NORMAL },
                onFailure: { [weak self] _ in self?.state = .ERROR }
            )
            .disposed(by: disposeBag)
    }

    func onFavouriteTapped(breed: Breed) {
        KMPNativeCoroutinesRxSwift.createSingle(for: onToggleFavouriteState.invokeNative(breed: breed))
            .subscribe(onFailure: { _ in })
            .disposed(by: disposeBag)
    }

    enum State { case NORMAL, LOADING, ERROR, EMPTY }
}
