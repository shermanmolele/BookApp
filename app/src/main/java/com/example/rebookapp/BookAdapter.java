package com.example.rebookapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BookAdapter  extends RecyclerView.Adapter<BookAdapter.BookViewHolder>  {

    ArrayList<Book> books;
    public BookAdapter(ArrayList<Book> books) {
        this.books = books;
    }

    //created when the recyclervieew meets a new viewholder
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View itemView  = LayoutInflater.from(context)
                .inflate(R.layout.book_list_item, viewGroup, false);

        return new BookViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, int i) {
         Book book = books.get(i);
         bookViewHolder.bind(book);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }



    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvTitle;
        TextView tvAuthors;
        TextView tvDate;
        TextView tvPublisher;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvAuthors= (TextView) itemView.findViewById(R.id.tvAuthors);
            tvDate = (TextView) itemView.findViewById(R.id.tvPublishedDate);
            tvPublisher =(TextView) itemView.findViewById(R.id.tvPublisher);
            itemView.setOnClickListener((View.OnClickListener) this);
        }
        public void bind(Book book) {
            tvTitle.setText(book.title);
            //authors is an array, deifine a string to contain all authors

            //loop through the authors

            tvAuthors.setText(book.authors);
            tvDate.setText(book.publishedDate);
            tvPublisher.setText(book.publisher);


        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Book selectedBook = books.get(position);
            Intent intent = new Intent(view.getContext(), BookActivity.class);
            intent.putExtra("Book", selectedBook);
            view.getContext().startActivity(intent);

        }
    }

}
