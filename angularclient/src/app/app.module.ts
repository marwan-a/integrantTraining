import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { PrivilegeListComponent } from './privilege-list/privilege-list.component';
import { PrivilegeService } from './service/privilege-service.service';
 
@NgModule({
  declarations: [
    AppComponent,
    PrivilegeListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [PrivilegeService],
  bootstrap: [AppComponent]
})
export class AppModule { }
