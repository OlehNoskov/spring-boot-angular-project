import {Component, OnInit} from '@angular/core';
import {ServerService} from "./service/server.service";
import {BehaviorSubject, map, Observable, of, startWith} from "rxjs";
import {AppState} from "./interface/app-state";
import {CustomResponse} from "./interface/custom-response";
import {DataState} from "./enum/data-state.enum";
import {catchError} from "rxjs/operators";
import {Status} from "./enum/status.enum";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  protected readonly Status = Status;
  appState$: Observable<AppState<CustomResponse>>;
  private filterSubject = new BehaviorSubject<string>('');
  filterStatus$ = this.filterSubject.asObservable();
  constructor(private serverService: ServerService) {
  }

  ngOnInit(): void {
    // @ts-ignore
    this.appState$ = this.serverService.servers$.pipe(map(response => {
        return {dataState: DataState.LOADED_STATE, appData: response}
      }),
      startWith({dataState: DataState.LOADING_STATE, appData: null}),
      catchError((error: string) => {
        return of({dataState: DataState.ERROR_STATE, error: error})
      })
    );
  }

  pingServer(ipAddress: string): void {
    this.filterSubject.next(ipAddress);
    // @ts-ignore
    this.appState$ = this.serverService.ping$(ipAddress).pipe(map(response => {
        return {dataState: DataState.LOADED_STATE, appData: response}
      }),
      startWith({dataState: DataState.LOADING_STATE, appData: null}),
      catchError((error: string) => {
        return of({dataState: DataState.ERROR_STATE, error: error})
      })
    );
  }
}
