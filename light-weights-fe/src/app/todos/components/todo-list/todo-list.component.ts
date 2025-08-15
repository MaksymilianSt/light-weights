import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf} from '@angular/common';
import {Todo} from '../../models/todo.model';
import {ActivatedRoute} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {PRIORITIES} from '../../models/priorities.const';
import {TodoComponent} from '../todo/todo.component';

@Component({
  selector: 'app-todo-list',
  imports: [
    NgForOf,
    NgIf,
    ReactiveFormsModule,
    FormsModule,
    TodoComponent
  ],
  templateUrl: './todo-list.component.html',
  standalone: true,
  styleUrl: './todo-list.component.css'
})
export class TodoListComponent implements OnInit {
  todoList!: Todo[];
  filteredTodoList: Todo[] = [];

  searchText: string | null = null;
  priorityFilter: string | null = null;
  doneFilter: boolean | null = null;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.todoList = this.route.snapshot.data['todos'];
    this.filteredTodoList = this.todoList;
  }

  filterTodos() {
    const lowerSearch = (this.searchText ?? '').toLowerCase();

    this.filteredTodoList = this.todoList.filter(todo =>
      (this.doneFilter == null || this.doneFilter === todo.done) &&
      (this.priorityFilter == null || this.priorityFilter === todo.priority) &&
      todo.note.toLowerCase().includes(lowerSearch)
    );
  }

  onNewTodo() {
    const newTodo:Todo = {
     id: -1,
     priority: 'LOW',
     done: false,
     deadline: null,
     note: ''
    } as Todo;

    this.todoList.unshift(newTodo);
    this.filteredTodoList = this.todoList;
  }

    addTodo(newTodo: Todo) {
    const index = this.todoList.findIndex(t => t.id === -1)
      if (index !== -1) {
        this.todoList[index] = newTodo;
      }
    }

    updateTodo(toUpdate: Todo) {
    const index = this.todoList.findIndex(t => t.id === toUpdate.id);
    if (index !== -1) {
      this.todoList[index] = toUpdate;
    }
  }

  removeFromList(todoId: number): void {
    this.todoList = this.todoList.filter(todo => todo.id !== todoId);
    this.filteredTodoList = this.todoList;
  }

  protected readonly PRIORITIES = PRIORITIES;
}
