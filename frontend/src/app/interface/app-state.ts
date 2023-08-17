import {DataState} from "../enum/data-state.enum";

export interface AppState<T> {
  data: DataState;
  appData: T;
  error: string;
}
