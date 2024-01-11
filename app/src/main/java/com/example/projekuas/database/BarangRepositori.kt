package com.example.projekuas.database

import android.content.ContentValues
import android.util.Log
import com.example.projekuas.Model.BarangSewa
import com.example.projekuas.Model.Pelanggan
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

interface BarangRepositori {
    fun getAll(): Flow<List<BarangSewa>>
    suspend fun save(barangSewa: BarangSewa): String
    suspend fun update(barangSewa: BarangSewa)
    suspend fun delete(barangSewaId: String)

    fun getAllWithPelanggan(): Flow<List<Pair<BarangSewa,Pelanggan?>>>

    fun getAllBasedOnPelangganId(pelangganId: String): Flow<Pair<BarangSewa,Pelanggan?>?>

    fun getBarangSewaByPelangganId(pelangganId: String) : Flow<BarangSewa?>
}

class BarangRepositoriImpl(private val firestore: FirebaseFirestore) : BarangRepositori {
    override fun getAll(): Flow<List<BarangSewa>> = flow {
        val snapshot = firestore.collection("BarangSewa")
            .orderBy("BarangSewa", Query.Direction.ASCENDING)
            .get()
            .await()
        val barangSewa = snapshot.toObjects(BarangSewa::class.java)
        emit(barangSewa)
    }.flowOn(Dispatchers.IO)

    override suspend fun save(barangSewa: BarangSewa): String {
        return try {
            val documentReference = firestore.collection("BarangSewa").add(barangSewa).await()

            firestore.collection("BarangSewa").document(documentReference.id)
                .set(barangSewa.copy(idSewa = documentReference.id))
            "Berhasil + ${documentReference.id}"
        } catch (e: Exception) {
            Log.w(ContentValues.TAG, "Error adding document", e)
            "Gagal $e"
        }
    }

    override suspend fun update(barangSewa: BarangSewa) {
        firestore.collection("BarangSewa").document(barangSewa.idSewa).set(barangSewa).await()
    }

    override suspend fun delete(barangSewaId: String) {
        val barangSewaSnapshot = firestore.collection("BarangSewa").whereEqualTo("idPelanggan", barangSewaId).get().await()
        barangSewaSnapshot.documents.forEach { document ->
            document.reference.delete()
        }

        val pelangganSnapshot = firestore.collection("Pelanggan").whereEqualTo("idPelanggan", barangSewaId).get().await()
        pelangganSnapshot.documents.forEach { document ->
            document.reference.delete()
        }
    }



    override fun getAllWithPelanggan(): Flow<List<Pair<BarangSewa, Pelanggan?>>> = flow {
        val snapshotBarangSewa = firestore.collection("BarangSewa").get().await()
        val listBarangSewa = snapshotBarangSewa.toObjects(BarangSewa::class.java)

        val snapshotPelanggan = firestore.collection("Pelanggan").get().await()
        val listPelanggan = snapshotPelanggan.toObjects(Pelanggan::class.java)

        val listResult = listBarangSewa.map { barangSewa -> Pair(barangSewa, listPelanggan.find { it.idPelanggan == barangSewa.idPelanggan }) }
        emit(listResult)
    }.flowOn(Dispatchers.IO)

    override fun getAllBasedOnPelangganId(barangSewaId: String): Flow<Pair<BarangSewa, Pelanggan?>?> = flow {
        val barangSewaSnapshot = firestore.collection("BarangSewa")
            .whereEqualTo("idPelanggan", barangSewaId)
            .get()
            .await()
        val pelangganSnapshot = firestore.collection("Pelanggan")
            .whereEqualTo("idPelanggan", barangSewaId)
            .get()
            .await()

        val barangSewaResult = barangSewaSnapshot.toObjects(BarangSewa::class.java)
        val pelangganResult = pelangganSnapshot.toObjects(Pelanggan::class.java)

        val barangSewa = barangSewaResult.firstOrNull()
        val pelanggan = pelangganResult.firstOrNull()

        val result = if (barangSewa != null) Pair(barangSewa, pelanggan) else null

        emit(result)
    }.flowOn(Dispatchers.IO)

    override fun getBarangSewaByPelangganId(barangSewaId: String): Flow<BarangSewa?> {
        return flow {
            val snapshot = firestore.collection("BarangSewa")
                .whereEqualTo("idPelanggan", barangSewaId)
                .get()
                .await()
            val barangSewa = snapshot.documents.firstOrNull()?.toObject(BarangSewa::class.java)
            emit(barangSewa)
        }.flowOn(Dispatchers.IO)
    }
}