export class TimeUtils {

  static getCurrentIsoDate(): string {
    return new Date().toISOString().split('Z')[0]
  }

  static parseTimeStringToSeconds(time: string): number {
    const [min, sec] = time.split(':').map(Number);
    return min * 60 + sec;
  }

  static getAverageBreakTime(sets: { executedAt: string | null }[]): string | null {
    const timestamps = sets
      .map(set => set.executedAt ? new Date(set.executedAt).getTime() : null)
      .filter((t): t is number => t !== null)
      .sort((a, b) => a - b);

    if (timestamps.length < 2) return null;

    const gaps: number[] = [];
    for (let i = 1; i < timestamps.length; i++) {
      gaps.push(timestamps[i] - timestamps[i - 1]);
    }

    const avgGapMs = gaps.reduce((acc, gap) => acc + gap, 0) / gaps.length;

    const totalSeconds = Math.floor(avgGapMs / 1000);
    const minutes = Math.floor(totalSeconds / 60);
    const seconds = totalSeconds % 60;

    return `${minutes.toString().padStart(2, '0')}:` +
      `${seconds.toString().padStart(2, '0')}`;
  }

  static calculateElapsedTime(startTime: string, endTime: string) {

    if (!startTime || !endTime || startTime == endTime) {
      return '00:00';
    }
    const end = new Date(endTime).getTime();
    const started = new Date(startTime).getTime();
    const diff = end - started;

    const hours = Math.floor(diff / (1000 * 60 * 60));
    const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
    const seconds = Math.floor((diff % (1000 * 60)) / 1000);

    return hours > 0
      ? `${this.pad(hours)}:${this.pad(minutes)}:${this.pad(seconds)}`
      : `${this.pad(minutes)}:${this.pad(seconds)}`
  }

  private static pad(n: number): string {
    return n < 10 ? '0' + n : n.toString();
  }
}
