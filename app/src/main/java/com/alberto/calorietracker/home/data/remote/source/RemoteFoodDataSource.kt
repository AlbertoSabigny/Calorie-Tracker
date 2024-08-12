package com.alberto.calorietracker.home.data.remote.source

import com.alberto.calorietracker.home.data.remote.model.FoodResponse
import com.alberto.calorietracker.home.data.remote.model.NutrientsResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteFoodDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun buscarAlimentos(query: String): List<FoodResponse> {
        return try {
            val snapshot = firestore.collection("Foods")
                .whereGreaterThanOrEqualTo("nombre", query)
                .whereLessThanOrEqualTo("nombre", query + '\uf8ff')
                .get()
                .await()

            snapshot.documents.mapNotNull { document ->
                document.toObject(FoodResponse::class.java)?.let { food ->
                    food.copy(
                        id = document.id,
                        nutrientes = cargarNutrientes(document.reference.collection("nutrientes"))
                    )
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun obtenerAlimentoPorId(id: String): FoodResponse? {
        return try {
            val document = firestore.collection("Foods").document(id).get().await()
            document.toObject(FoodResponse::class.java)?.let { food ->
                food.copy(
                    id = document.id,
                    nutrientes = cargarNutrientes(document.reference.collection("nutrientes"))
                )
            }
        } catch (e: Exception) {
            null
        }
    }


    private suspend fun cargarNutrientes(collection: com.google.firebase.firestore.CollectionReference): List<NutrientsResponse> {
        return try {
            val nutrientesSnapshot = collection.get().await()
            nutrientesSnapshot.documents.mapNotNull { nutrienteDoc ->
                nutrienteDoc.toObject(NutrientsResponse::class.java)?.copy(id = nutrienteDoc.id)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}