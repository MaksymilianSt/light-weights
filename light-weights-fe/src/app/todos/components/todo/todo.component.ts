import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Todo} from '../../models/todo.model';
import {PRIORITIES} from '../../models/priorities.const';
import {NgClass, NgForOf, NgIf} from '@angular/common';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {TodoService} from '../../services/todo-service';

@Component({
  selector: 'app-todo',
  imports: [
    NgForOf,
    ReactiveFormsModule,
    FormsModule,
    NgClass,
    NgIf
  ],
  templateUrl: './todo.component.html',
  standalone: true,
  styleUrl: './todo.component.css'
})
export class TodoComponent implements OnInit {
  readonly NEW_TODO_ID = -1;
  @Input() todo: Todo | null = null;
  createMode: boolean = false;
  todoForm!: FormGroup;

  @Output() removeFromList = new EventEmitter<number>();
  @Output() updateInList = new EventEmitter<Todo>();
  @Output() addNewTodo = new EventEmitter<Todo>();

  constructor(
    private fb: FormBuilder,
    private todoService: TodoService,
  ) {
  }

  ngOnInit(): void {
    this.createMode = this.todo?.id === this.NEW_TODO_ID;
    this.initForm();
  }

  private initForm() {
    this.todoForm = this.fb.group({
      deadline: [this.todo?.deadline ?? null],
      priority: [this.todo?.priority ?? 'LOW'],
      done: [this.todo?.done ?? false],
      note: [this.todo?.note ?? null, [Validators.required, Validators.pattern(/\S+/)] ],
    });
  }

  protected readonly PRIORITIES = PRIORITIES;

  onSave() {
    const toSave = {
      id: this.todo?.id,
      deadline: this.todoForm.controls['deadline'].value,
      priority: this.todoForm.controls['priority'].value,
      done: this.todoForm.controls['done'].value,
      note: this.todoForm.controls['note'].value
    } as Todo;

    if (this.createMode) {
      this.todoService.create(toSave).subscribe(createdTodo => {
        this.todo = createdTodo;
        this.createMode = false;
        this.initForm();
        this.addNewTodo.emit(this.todo);
      })
    } else {
      this.todoService.update(toSave).subscribe(updatedTodo => {
        this.todo = updatedTodo;
        this.initForm();
        this.updateInList.emit(this.todo);
      })
    }
  }

  onDelete() {
    if (this.todo?.id === this.NEW_TODO_ID) {
      this.removeFromList.emit(this.todo!.id)
      return
    }
    this.todoService.delete(this.todo!.id).subscribe(() => {
      this.removeFromList.emit(this.todo!.id)
    })
  }

}
