export interface ExamenQuestion {
    questionId: number; // Id de la question
    chapitre: number;
    questionText: string; // Énoncé de la question
    questionScore: number;
    options: string[];
}