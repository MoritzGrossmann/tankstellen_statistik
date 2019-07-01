using System;
using Microsoft.FSharp.Core;

namespace Grossmann.Tankerapi.Client.Core
{
    public static class FSharpOptionExtensions
    {
        public static bool IsNone<T>(this FSharpOption<T> option)
        {
            return FSharpOption<T>.get_IsNone(option);
        }

        public static T ValueOrDefault<T>(this FSharpOption<T> option, T defaultValue)
        {
            return option.IsNone() ? defaultValue : option.Value;
        }

        public static FSharpOption<TR> Bind<T, TR>(this FSharpOption<T> option, Func<T, FSharpOption<TR>> f)
        {
            return option.IsNone() ? FSharpOption<TR>.None : f(option.Value);
        }

        public static FSharpOption<TR> Map<T, TR>(this FSharpOption<T> option, Func<T, TR> f)
        {
            return option.IsNone() ? FSharpOption<TR>.None : FSharpOption<TR>.Some(f(option.Value));
        }
    }
}