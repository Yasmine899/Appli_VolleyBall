import { Component, Input} from '@angular/core';

@Component({
  selector: 'app-caracteristiques-du-jeu',
  standalone: true,
  imports: [],
  templateUrl: './caracteristiques-du-jeu.component.html',
  styleUrl: './caracteristiques-du-jeu.component.scss'
})
export class CaracteristiquesDuJeuComponent {
  @Input() title: string = "";
}
