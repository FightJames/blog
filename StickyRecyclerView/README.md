This is StickyRecyclerView.

![image](https://github.com/FightJames/blog/blob/master/StickyRecyclerView/gif_20180805_140632.gif)

How to use
```
    <com.techapp.james.stickyrecyclerview.StickyRecyclerView
        android:id="@+id/recyclerListVertical"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:enableDivider="true"
        app:isVertical="true"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="1.0"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/recyclerListHorizon" />
```
app:enableDivider="true"
To display divider.

app:isVertical="true"
To adjust View is vertical or horizon


Insert Data
```
        val title = "abcdefghijklmnopqrst"
        val item = "50412789458263"
        title.forEach { t ->
            item.forEach { i ->
                recyclerListVertical.insertOrUpdate(t.toString(), i.toString())
                recyclerListHorizon.insertOrUpdate(t.toString(), i.toString())
            }
        }
```
