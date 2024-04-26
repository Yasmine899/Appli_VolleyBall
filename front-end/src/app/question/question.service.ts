import { Injectable } from '@angular/core';
import { Question } from './question'; // Importer l'interface ou la classe Question

@Injectable({
  providedIn: 'root'
})

export class QuestionService {
  private questions: Question[] = [
    {
      statement: "Quelles sont les dimensions minimales de l'aire de jeu ?",
      options: ["au minimum 9m x 18m", "au minimum 9m x 9m", "au minimum 15m x 24m"],
      correctAnswers: [2]
    },
    {
      statement: "La surface de jeu ...",
      options: ["ne doit présenter aucun risque de blessure pour les joueurs", "est plate", "n'est pas glissante ni rugueuse", "est horizontale et uniforme"],
      correctAnswers: [0,1,2,3]
    },
    {
      statement: "De quelle couleur doit-être la surface de jeu?",
      options: ["Peut importe", "De couleur claire", "De couleur foncée"],
      correctAnswers: [1]
    },
  ]; 
  
  constructor() { }

  getQuestions(): Question[] {
    return this.questions;
  }
}
