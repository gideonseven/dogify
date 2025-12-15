//
//  BreedsGridUIView.swift
//  iosApp
//
//  Created by gideon tobing on 15/12/2025.
//

import SwiftUI
import ComposeApp

struct BreedsGridUIView: View {
    
    var breeds: Array<Breed>
    var onFavouriteTapped: (Breed) -> Void = {_ in}
    
    var body: some View {
          let columns = [
              GridItem(.flexible(minimum: 128, maximum: 256), spacing: 16),
              GridItem(.flexible(minimum: 128, maximum: 256), spacing: 16)
          ]
          ScrollView{
              LazyVGrid(columns: columns, spacing: 16){
                  ForEach(breeds, id: \.name){ breed in
                      BreedUIView(breed: breed, onFavouriteTapped: onFavouriteTapped)
                  }
              }.padding(.horizontal, 16)
          }
      }
}

#Preview {
    BreedsGridUIView(breeds: Array<Breed>(
               arrayLiteral:
                   Breed(
                       name: "beagle",
                       imageUrl: "https://images.dog.ceo//breeds//beagle//n02088364_161.jpg",
                       isFavourite: false
                   ),
               Breed(
                   name: "affenpinscher",
                   imageUrl: "https://images.dog.ceo//breeds//affenpinscher//n02110627_3001.jpg",
                   isFavourite: true
               )
           ))
}
