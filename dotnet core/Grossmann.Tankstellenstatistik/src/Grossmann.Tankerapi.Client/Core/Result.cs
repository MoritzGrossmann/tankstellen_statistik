using System;
using Grossmann.Tankerapi.Client.Core;
using Microsoft.FSharp.Core;
using Newtonsoft.Json;
using JsonSerializer = RestSharp.Serialization.Json.JsonSerializer;

namespace Grossmann.Tankerapi.Client.Core
{
    public class Result<TE, TA>
    {
        private bool _error;

        private TE _err;

        private TA _value;

        public static Result<TE, TA> Error(TE error)
        {
            return new Result<TE, TA>()
            {
                _error = true,
                _err = error
            };
        }

        public static Result<TE, TA> Ok(TA value)
        {
            return new Result<TE, TA>()
            {
                _error = false,
                _value = value
            };
        }

        public bool IsError() => _error;

        public TA Value
        {
            get
            {
                if (_error)
                    throw new ArgumentException();
                return _value;
            }
        }

        public TE Err
        {
            get
            {
                if (!_error)
                    throw new ArgumentException();
                return _err;
            }
        }

        public Result<TE, TB> Map<TB>(Func<TA, TB> f)
        {
            if (IsError())
                return Result<TE, TB>.Error(Err);

            return Result<TE, TB>.Ok(f(Value));
        }

        public Result<TE, TC> Map2<TB, TC>(Result<TE, TB> second, Func<TA, TB, TC> f)
        {
            if (IsError() || second.IsError())
                return Result<TE, TC>.Error(Err);

            return Result<TE, TC>.Ok(f(Value, second.Value));
        }

        public Result<TE, TD> Map3<TB, TC, TD>(Result<TE, TB> second, Result<TE, TC> third, Func<TA, TB, TC, TD> f)
        {
            if (IsError() || second.IsError() || second.IsError())
                return Result<TE, TD>.Error(Err);

            return Result<TE, TD>.Ok(f(Value, second.Value, third.Value));
        }

        public TA WithDefault(TA def)
        {
            if (IsError())
                return def;
            return Value;
        }

        public FSharpOption<TA> ToOption()
        {
            return IsError() ? FSharpOption<TA>.None : FSharpOption<TA>.Some(Value);
        }

        public static Result<TE, TA> FromOption(FSharpOption<TA> option, TE error)
        {
            return option.IsNone() ? Error(error) : Ok(option.Value);
        }

        public Result<TE, TB> AndThen<TB>(Func<TA, Result<TE, TB>> f)
        {
            return IsError() ? Result<TE, TB>.Error(Err) : f(Value);
        }
    }
}

namespace MM.Eingangsmeldung.Extern.Core.Result
{
    public static class ResultExtension
    {
        public static Result<TE, TB> Applicative<TE, TA, TB>(this Result<TE, Func<TA, TB>> fRes, Result<TE, TA> aRes)
        {
            if (fRes.IsError() || aRes.IsError())
                return Result<TE, TB>.Error(fRes.Err);

            return Result<TE, TB>.Ok(fRes.Value(aRes.Value));
        }
    }
}