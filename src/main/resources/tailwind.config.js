const typography = require("@tailwindcss/typography");

/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./templates/**/*.html"],
  theme: {
    container: {
      center: true,
      padding: "1rem",
    },
    screens: {
      sm: "640px",
      md: "768px",
      lg: "1024px",
      xl: "1280px",
    },
    fontFamily: {
      manrope: ["Manrope", "sans-serif"],
    },
    extend: {
      boxShadow: {
        center: "0 0 20px rgba(0, 0, 0, 0.1)",
      },
      colors: {
        border: "#e5e7eb",
        transparent: "transparent",
        current: "currentColor",
        white: "#ffffff",
        black: "#010717",
        primary: "#a05a42",
        "primary-foreground": "#ffffff",
        gray: {
          lighter: "#FAF7F3",
          light: "#323232",
          dark: "#010717",
          txt: "#888888",
          line: "#E5E5E5",
        },
        red: {
          DEFAULT: "#d70018",
        },
        yellow: {
          DEFAULT: "#ffec3d",
        },
      },
    },
  },
  plugins: [typography],
};
