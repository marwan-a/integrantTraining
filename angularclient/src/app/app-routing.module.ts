import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PrivilegeListComponent } from './privilege-list/privilege-list.component';
 
const routes: Routes = [
  { path: 'privileges', component: PrivilegeListComponent }
];
 
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }