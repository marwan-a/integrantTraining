import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Privilege } from '../model/privilege';
import { Observable } from 'rxjs/Observable';
 
@Injectable()
export class PrivilegeService {
 
  private privilegesUrl: string;
 
  constructor(private http: HttpClient) {
    this.privilegesUrl = 'http://localhost:8080/privileges';
  }
 
  public findAll(): Observable<Privilege[]> {
    return this.http.get<Privilege[]>(this.privilegesUrl);
  }
}