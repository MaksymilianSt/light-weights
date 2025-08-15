import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Todo} from '../models/todo.model';
import {environment} from '../../../../environment';

@Injectable({
  providedIn: 'root',
})
export class TodoService {
  private readonly URL = `${environment.apiUrl}/todos`;

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Todo[]> {
    return this.http.get<Todo[]>(this.URL);
  }

  update(todo: Todo): Observable<Todo> {
    return this.http.put<Todo>(
      `${this.URL}/${todo.id}`,
      todo
    );
  }

  create(todo: Todo): Observable<Todo> {
    return this.http.post<Todo>(
      `${this.URL}`,
      todo
    );
  }

  delete(todoId: number): Observable<void> {
    return this.http.delete<void>(`${this.URL}/${todoId}`);
  }

}
