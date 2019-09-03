import { Component, OnInit } from '@angular/core';
import { Privilege } from '../model/privilege';
import { PrivilegeService } from '../service/privilege-service.service';
 
@Component({
  selector: 'app-privilege-list',
  templateUrl: './privilege-list.component.html',
  styleUrls: ['./privilege-list.component.css']
})
export class PrivilegeListComponent implements OnInit {
 
  privileges: Privilege[];
 
  constructor(private privilegeService: PrivilegeService) {
  }
 
  ngOnInit() {
    this.privilegeService.findAll().subscribe(data => {
      this.privileges = data;
    });
  }
}