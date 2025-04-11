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
    colors: {
      transparent: "transparent",
      current: "currentColor",
      white: "#ffffff",
      black: "#010717",
      primary: "#da5036",
      "primary-forground": "#ffffff",
      gray: {
        lighter: "#FAF7F3",
        light: "#323232",
        dark: "#010717",
        txt: "#888888",
        line: "#E5E5E5",
      },
    },
    extend: {
      boxShadow: {
        center: "0 0 20px rgba(0, 0, 0, 0.2)",
      },
    },
  },
  plugins: [typography],
};
