import { Component } from '@angular/core';
import { MetaService } from '../../includes/all.services';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  
  constructor(private meta: MetaService) {
    
  }
}
