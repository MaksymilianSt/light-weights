import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {Todo} from '../models/todo.model';
import {TodoService} from '../services/todo-service';


@Injectable({providedIn: 'root'})
export class TodosResolver implements Resolve<Todo[]> {

  constructor(private todoService: TodoService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Todo[]> {
    return this.todoService.getAll();
  }
}
