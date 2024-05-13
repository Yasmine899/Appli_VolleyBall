import { Component } from '@angular/core';

@Component({
  selector: 'app-assistance',
  standalone: true,
  imports: [],
  template: `
    <div id="container">
      <p class="txt">
      Pour toute question concernant le contenu du cours, vous pouvez contacter X à Y. 
      </p>
      <br>
      <p class="txt">
      Pour toute question relative aux bogues ou au support technique lié à cette page web, veuillez contacter U à V.
      </p>
    </div>
  `,
  styleUrl: './assistance.component.scss'
})
export class AssistanceComponent {

}
