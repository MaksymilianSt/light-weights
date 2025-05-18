export interface JwtPayload {
  sub: string;
  exp: number;
  iat?: number;
  [key: string]: any;
}
