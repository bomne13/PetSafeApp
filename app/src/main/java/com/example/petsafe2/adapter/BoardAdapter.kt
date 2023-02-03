package com.example.petsafe2.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.petsafe2.view.MainViewModel
import com.example.petsafe2.databinding.ListItemBoardBinding
import com.example.petsafe2.model.Board
import com.example.petsafe2.view.fragment.BoardFragmentDirections



class BoardAdapter(private val mainViewModel: MainViewModel): RecyclerView.Adapter<BoardAdapter.BoardHolder>(){

    companion object{
        const val TAG = "PetSafe2:: BoardAdapter"
    }

    private var boardList = emptyList<Board>()

    class BoardHolder(private val binding: ListItemBoardBinding): RecyclerView.ViewHolder(binding.root){
        lateinit var board: Board
        lateinit var mainViewModel: MainViewModel

        fun bind(currentBoard: Board, mainViewModel: MainViewModel){
            binding.board = currentBoard
            this.mainViewModel = mainViewModel

            itemView.setOnClickListener{
                val navAction = BoardFragmentDirections.actionBoardToDetail(board = arrayOf(currentBoard))
                Navigation.createNavigateOnClickListener(navAction).onClick(itemView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardHolder {
        val binding = ListItemBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BoardHolder(binding)
    }

    override fun onBindViewHolder(holder: BoardHolder, position: Int) {
        holder.bind(boardList[position], mainViewModel)
    }

    override fun getItemCount(): Int {
        return boardList.size
    }

    fun setData(board: List<Board>){
        Log.d(TAG, "setData: setData 실행 $board")
        boardList = board
        //recyclerview 리스트 업데이트(리스트 크기와 아이템이 둘다 변경되는 경우에 주로 사용)
        notifyDataSetChanged()
    }

}
