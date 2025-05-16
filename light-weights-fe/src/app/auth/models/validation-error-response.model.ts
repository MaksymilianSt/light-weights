export interface ValidationErrorResponse {
  message: string;
  errors: {
    [field: string]: string[];
  };
}
