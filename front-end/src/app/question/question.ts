export interface Question {
    statement: string; // Énoncé de la question
    options: string[]; // Liste des réponses possibles
    correctAnswers: number[]; // Index des réponses valides dans la liste des options
}
  