import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [],
  template: `
  <div id="container">
    <div id="signin">
      <h1>Pas de compte ?</h1>
      <p>
        Si vous êtes enseignant, vous pouvez demander à créer un compte ici. 
        Un compte vous donnera accès aux examens finaux. 
        Votre demande sera approuvée ou refusée par l'un de nos modérateurs, et vous serez notifié par courriel. 
        Si votre demande est approuvée, nos modérateurs vous fourniront un nom d'utilisateur et un mot de passe.
      </p>
      <div class="form">
        <input class="signin_input" id="last_name" placeholder="Nom"><br><br>
        <input class="signin_input" id="first_name" placeholder="Prénom"><br><br>
        <input class="signin_input" id="establishment" placeholder="Établissement"><br><br>
        <input class="signin_input" id="mail" placeholder="e-mail">
      </div>
      <button class="btn" style="color: #007DA0; background-color: white;" (click)="sign_in()">S'inscrire</button>
    </div>
    <div id="login">
      <h1>Connectez-vous à votre compte</h1>
      <p>
        Si vous avez déjà un compte, vous pouvez vous connecter ici en utilisant le nom d'utilisateur et le mot de passe qui vous ont été fournis par nos modérateurs.
      </p>
      <div class="form">
        <input class="login_input" id="username" placeholder="Nom d'utilisateur"><br><br>
        <input class="login_input" id="password" placeholder="Mot de passe">
      </div>
      <button class="btn" (click)="log_in()">Se connecter</button>
    </div>
  </div>
  `,
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  log_in() {

  }

  sign_in() {

  }

}
