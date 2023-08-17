import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {catchError, tap} from "rxjs/operators";
import {CustomResponse} from "../interface/custom-response";
import {Server} from "../interface/server";
import {Status} from "../enum/status.enum";

@Injectable({providedIn: 'root'})
export class ServerService {
  private readonly url?: 'http://localhost:8080';

  constructor(private http: HttpClient) {
  }

  servers$ = <Observable<CustomResponse>>
    this.http.get<CustomResponse>(`${this.url}/server/list`).pipe(
      tap(console.log), catchError(this.handleError)
    );

  save$ = (server: Server) => <Observable<CustomResponse>>
    this.http.post<CustomResponse>(`${this.url}/server/save`, server).pipe(
      tap(console.log), catchError(this.handleError)
    );

  ping$ = (ipAddress: string) => <Observable<CustomResponse>>
    this.http.get<CustomResponse>(`${this.url}/server/ping/${ipAddress}`).pipe(
      tap(console.log), catchError(this.handleError)
    );

  delete$ = (serverId: number) => <Observable<CustomResponse>>
    this.http.delete<CustomResponse>(`${this.url}/server/delete/${serverId}`).pipe(
      tap(console.log), catchError(this.handleError)
    );

  filter$ = (status: Status, response: CustomResponse) => <Observable<CustomResponse>>
    new Observable<CustomResponse>(subscriber => {
      console.log(response);
      subscriber.next(
        status === Status.ALL
          ? {...response, message: `Servers filtered by ${status} status`}
          : {
            ...response, message: response.data.servers.filter(server =>
              server.status === status).length > 0
              ? `Servers filtered by ${status === Status.SERVER_UP
                ? 'SERVER_UP' : 'SERVER_DOWN'} status`
              : 'No servers of ${status} found!',
            data: {
              servers: response.data.servers.filter(server => server.status === status)
            }
          });
      subscriber.complete();
    }).pipe(tap(console.log), catchError(this.handleError));


  private handleError(error: HttpErrorResponse): Observable<never> {
    console.log(error);
    return throwError(`An error occurred - Error code:${error.status}`)
  }
}
